package com.amcoder.pranku.Utility;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.amcoder.pranku.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


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

    private void getProductsFromFireBase(String tableName) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(tableName);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

}