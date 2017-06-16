package com.amcoder.pranku.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.activeandroid.query.Select;
import com.amcoder.pranku.R;
import com.amcoder.pranku.address.AddressFields;
import com.amcoder.pranku.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PrankUtility {
    int loopCount = 0;

    @SuppressWarnings("VisibleForTests")
    public void UploadImageRecursive(StorageReference storageReference, ArrayList<byte[]> images, final Product product) {

        int noOfFiles = images.size();

        if (noOfFiles == 0) return;

        final ArrayList<String> uploadedUrls = new ArrayList<>();

        UploadTask uploadTask = storageReference.putBytes(images.get(0));

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", " addOnFailureListener ");


            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //taskSnapShot u will get the download url.
                uploadedUrls.add(taskSnapshot.getDownloadUrl().toString());
                product.setUrls(uploadedUrls);

                sendObjectToFireBase(product, "products");
                // uploadedUrls.add(taskSnapshot.getDownloadUrl());
                // loopCount++;
            }
        });
    }

    public StorageReference getStorageReference(String fileName) {

        // Create a storage reference from our app
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference mountainImagesRef = storageRef.child("images/" + fileName);

        return mountainImagesRef;
    }

    public void sendObjectToFireBase(Product product, String tableName) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String userId = mDatabase.push().getKey();
        mDatabase.child(tableName).child(userId).setValue(product);

    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        Log.v("Bitmap width - Height :", width + " : " + height);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public byte[] getBytesFromBitMap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static int getThemeColor(Context context) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionMenuTextColor});
        int mThemeBaseColor = a.getColor(0, ContextCompat.getColor(context, R.color.app_primary_dark));
        a.recycle();
        return mThemeBaseColor;
    }

    public static void hideKeypad(Context pContext) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                pContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (null != ((Activity) pContext).getCurrentFocus()) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) pContext).getCurrentFocus().getWindowToken(),
                    0);
        }
    }

    public static void saveAddressToDB(AddressFields addressFields) {
        addressFields.save();
    }

    public static List<AddressFields> getAddressList() {

        return new Select().from(AddressFields.class).execute();
    }


    protected static void appendAddressWithNewLineIfNotNull(StringBuilder sb, String code) {
        if (!TextUtils.isEmpty(code)) {
            sb.append(code).append(Constant.NEW_LINE_ESCAPE_CHARACTER);
        }
    }

    public static String formatAddress(final String address) {
        if (address != null)
            return address.replaceAll(", ", "\n");
        else
            return null;
    }

    public static String getAddressToDisplay(final AddressFields address) {
        StringBuilder sb = new StringBuilder();
        appendAddressWithNewLineIfNotNull(sb, address.getLine1());
        appendAddressWithNewLineIfNotNull(sb, address.getLine2());
        appendAddressWithNewLineIfNotNull(sb, address.getRegionName());
        appendAddressWithNewLineIfNotNull(sb, address.getTown());
        appendAddressWithNewLineIfNotNull(sb, address.getPostalCode());
        return sb.toString();
    }

    private static boolean isNotNullNorEmpty(String field) {
        return !TextUtils.isEmpty(field);
    }

    public static AddressFields prepareAddressFields(AddressFields addresses, String janRainEmail) {
        AddressFields fields = new AddressFields();

        if (isNotNullNorEmpty(addresses.getFirstName())) {
            fields.setFirstName(addresses.getFirstName());
        }

        if (isNotNullNorEmpty(addresses.getLastName())) {
            fields.setLastName(addresses.getLastName());
        }

        if (isNotNullNorEmpty(addresses.getTitleCode())) {
            String titleCode = addresses.getTitleCode();
            if (titleCode.trim().length() > 0)
                fields.setTitleCode(titleCode.substring(0, 1).toUpperCase(Locale.getDefault())
                        + titleCode.substring(1));
        }

        if (isNotNullNorEmpty(addresses.getLine1())) {
            fields.setLine1(addresses.getLine1());
        }

        if (isNotNullNorEmpty(addresses.getLine2())) {
            fields.setLine2(addresses.getLine2());
        }

        if (isNotNullNorEmpty(addresses.getTown())) {
            fields.setTown(addresses.getTown());
        }

        if (isNotNullNorEmpty(addresses.getPostalCode())) {
            fields.setPostalCode(addresses.getPostalCode());
        }

        if (isNotNullNorEmpty(addresses.getCountryIsocode())) {
            fields.setCountryIsocode(addresses.getCountryIsocode());
        }

        if (isNotNullNorEmpty(addresses.getEmail())) {
            fields.setEmail(addresses.getEmail());
        } else {
            fields.setEmail(janRainEmail); // Since there is no email response from hybris
        }

        if (isNotNullNorEmpty(addresses.getPhoneNumber())) {
            fields.setPhoneNumber(addresses.getPhoneNumber());
        }

        if (addresses.getRegionName() != null) {
            fields.setRegionName(addresses.getRegionName());
        }
        return fields;
    }
}
