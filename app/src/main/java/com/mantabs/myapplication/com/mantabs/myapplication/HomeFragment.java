package com.mantabs.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    private String URL="http://bazings.sveg.xyz/";
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Data> listdata;

    private GridLayoutManager layoutManager;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = (SliderView) rootView.findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.pagesContainer);
        setupOfSlider();

        // Inflate the layout for this fragment
        return rootView;
        recyclerView=(RecyclerView) recyclerView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager= new GridLayoutManager(this,2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        listdata = new ArrayList<Data>();
        AmbilData();
        recyclerAdapter = new RecyclerAdapter(this,listdata);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

   // private void setupSlider() {
     //   sliderView.setDurationScroll(800);
     //   List<Fragment> fragments = new ArrayList<>();
     //   fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-1.jpg"));
     //   fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-2.jpg"));
     //   fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-3.jpg"));
     //   fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-4.jpg"));

     //   mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
     //   sliderView.setAdapter(mAdapter);
     //   mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
     //   mIndicator.setPageCount(fragments.size());
     //   mIndicator.show();
   // }
    private void setupOfSlider() {
        sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSlider.newInstance("image-slider-1.jpg"));
        fragments.add(FragmentSlider.newInstance("image-slider-2.jpg"));
        fragments.add(FragmentSlider.newInstance("image-slider-3.jpg"));


        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

    public void AmbilData(){
        JsonArrayRequest aarRequest = new JsonArrayRequest(URL + "/produk.php",new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject data = response.getJSONObject(i);
                            Data item = new Data();
                            item.setId(data.getString("id"));
                            item.setJudul(data.getString("judul"));
                            item.setHarga(data.getString("harga"));
                            item.setThumbnail(URL + "/img/" + data.getString("gambar"));
                            listdata.add(item);
                            recyclerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                        }
                    }
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){






        };
        Volley.newRequestQueue(this).add(aarRequest);

    }

}


