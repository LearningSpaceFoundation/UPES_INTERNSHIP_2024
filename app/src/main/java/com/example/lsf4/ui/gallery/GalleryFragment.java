package com.example.lsf4.ui.gallery;

import static android.R.layout.simple_list_item_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lsf4.R;
import com.example.lsf4.databinding.FragmentGalleryBinding;
import com.example.lsf4.ui.course1;
import com.example.lsf4.ui.course2;

public class GalleryFragment extends Fragment {
    ListView listView;
    String arr[]={"Course 1","Course 2","Course 3","Course 4","Course 5"};
   // int imgid[] = {R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,};
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = (ListView) root.findViewById(R.id.list);
        ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arr);
        listView.setAdapter(ad);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(getContext(), "Course 1", Toast.LENGTH_SHORT).show();
                    Intent ac = new Intent(getContext(), course1.class);
                    startActivity(ac);
                } else if (position == 1) {
                    Toast.makeText(getContext(), "Course 2", Toast.LENGTH_SHORT).show();
                    Intent as = new Intent(getContext(), course2.class);
                    startActivity(as);
                } else if (position == 2) {
                    Toast.makeText(getContext(), "Course 3", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {
                    Toast.makeText(getContext(), "Course 4", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {
                    Toast.makeText(getContext(), "Course 5", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}