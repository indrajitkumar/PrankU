package com.amcoder.pranku;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.amcoder.pranku.Utility.PrankUtility;
import com.amcoder.pranku.fragment.ProductFormDialog;
import com.amcoder.pranku.model.Product;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class UpLoadFileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 100;
    Button uploadBtn;
    PrankUtility utility;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_fragment_layout, container, false);
        uploadBtn = (Button) view.findViewById(R.id.upload_btn);
        utility = new PrankUtility();

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*StorageReference storageReference = utility.getStorageReference("potato.jpg");
                ArrayList<byte[]> arrayList = new ArrayList<>();
                Drawable prankclubs = getResources().getDrawable(R.drawable.prankclubs);
                Bitmap bitmap = utility.drawableToBitmap(prankclubs);
                arrayList.add(utility.getBytesFromBitMap(bitmap));
                utility.UploadImageRecursive(storageReference, arrayList, new Product("Potato", "Hit on face", "Rs 20.00 Kg", "Rs 18.00Kg", "Pota_20"));*/
                showFillProductFormDialog();

            }
        });
        return view;
    }




    private void showFillProductFormDialog() {

        ProductFormDialog dialog = new ProductFormDialog ();
        dialog.show(getActivity().getFragmentManager(), "example");
    }
}
