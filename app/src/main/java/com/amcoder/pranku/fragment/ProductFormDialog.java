package com.amcoder.pranku.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amcoder.pranku.R;
import com.amcoder.pranku.Utility.PrankUtility;
import com.amcoder.pranku.model.Product;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ProductFormDialog extends DialogFragment implements View.OnClickListener{


    private static final int PICK_IMAGE_REQUEST = 100;
    EditText mEtTitle,mEtDescription,mEtPrice,mEtDiscountPrice,mEtProductId,mEtPhotoName;
    Button mBtnCancel,mBtnPickPhoto,mBtnUpload;
    private PrankUtility utility;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_product_form, container,
                false);

        mEtTitle=(EditText)rootView.findViewById(R.id.et_title);
        mEtDescription=(EditText)rootView.findViewById(R.id.et_description);
        mEtPrice=(EditText)rootView.findViewById(R.id.et_price);
        mEtDiscountPrice=(EditText)rootView.findViewById(R.id.et_discount_price);
        mEtProductId=(EditText)rootView.findViewById(R.id.et_product_id);
        mEtPhotoName=(EditText)rootView.findViewById(R.id.et_photo_name);

        mBtnCancel=(Button)rootView.findViewById(R.id.btn_cancel);
        mBtnPickPhoto=(Button)rootView.findViewById(R.id.btn_pick_photo);
        mBtnUpload=(Button)rootView.findViewById(R.id.btn_upload);

        mBtnPickPhoto.setEnabled(false);
        mBtnUpload.setEnabled(false);

        mBtnCancel.setOnClickListener(this);
        mBtnPickPhoto.setOnClickListener(this);
        mBtnUpload.setOnClickListener(this);

        utility = new PrankUtility();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                enableSelectPhotoButton();
            }
        };

        mEtTitle.addTextChangedListener(tw);
        mEtDescription.addTextChangedListener(tw);
        mEtPrice.addTextChangedListener(tw);
        mEtDiscountPrice.addTextChangedListener(tw);
        mEtProductId.addTextChangedListener(tw);
        mEtPhotoName.addTextChangedListener(tw);
    }


    private void enableSelectPhotoButton() {
        mBtnPickPhoto.setEnabled(mEtTitle.getText().length() > 0 &&
                mEtDescription.getText().length() > 0 &&
                mEtPrice.getText().length() > 0 &&
                mEtDiscountPrice.getText().length() > 0 &&
                mEtProductId.getText().length() > 0 &&
                mEtPhotoName.getText().length() > 0);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_cancel:
                 getDialog().dismiss();
                break;

            case R.id.btn_pick_photo:
                startPhotoPicker();
                break;

            case R.id.btn_upload:

                StorageReference storageReference = utility.getStorageReference(mEtPhotoName.getText().toString().trim() + ".jpg");
                ArrayList<byte[]> arrayList = new ArrayList<>();
                arrayList.add(utility.getBytesFromBitMap(mSelectedBitmap));
                utility.UploadImageRecursive(storageReference, arrayList, new Product(mEtTitle.getText().toString().trim(),
                        mEtDescription.getText().toString().trim(), mEtPrice.getText().toString() +" Rs".trim(),
                        mEtDiscountPrice.getText().toString().trim() +" Rs",mEtProductId.getText().toString().trim()));



                getDialog().dismiss();
                break;
        }
    }

    private void startPhotoPicker(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    Bitmap mSelectedBitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            if(data==null)return;
            Uri uri = data.getData();

            try {
                mSelectedBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                if(mSelectedBitmap!=null){

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Photo attached successful",Toast.LENGTH_SHORT).show();
                        }
                    });
                    mBtnUpload.setEnabled(true);

                }else{

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Photo attached Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                    mBtnUpload.setEnabled(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
