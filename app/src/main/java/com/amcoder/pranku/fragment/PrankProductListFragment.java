package com.amcoder.pranku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amcoder.pranku.R;
import com.amcoder.pranku.adapters.ProductListAdapter;
import com.amcoder.pranku.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PrankProductListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ProductListAdapter mAdapter;
    private ArrayList<Product> mProductList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.productlist_fragment_layout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.product_catalog_recycler_view);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("products");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Object value = dataSnapshot.getValue();
                System.out.print(dataSnapshot.toString());

                getProductInfoFromDataSnapshot((HashMap) value);
                mAdapter = new ProductListAdapter(mContext, mProductList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return view;
    }

    private void getProductInfoFromDataSnapshot(HashMap value) {
        HashMap map = value;
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println("Key: " + pairs.getKey() + " Val: " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException

            HashMap pairsValue = (HashMap) pairs.getValue();
            System.out.println(pairsValue);

            Product product = new Product();
            ArrayList<String> urls = (ArrayList<String>) pairsValue.get("urls");
            product.setUrls(urls);
            product.setDescription(pairsValue.get("price").toString());
            product.setDiscountPrice(pairsValue.get("title").toString());
            product.setPrice(pairsValue.get("productID").toString());
            product.setProductID(pairsValue.get("description").toString());
            product.setTitle(pairsValue.get("discountPrice").toString());
            mProductList.add(product);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
