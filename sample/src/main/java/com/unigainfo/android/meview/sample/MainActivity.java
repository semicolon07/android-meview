package com.unigainfo.android.meview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.unigainfo.android.meview.adapter.MeFlexAdapter;
import com.unigainfo.android.meview.adapter.model.DividerItem;
import com.unigainfo.android.meview.onechoice.ChoiceGroup;
import com.unigainfo.android.meview.sample.model.HeaderViewItemModel;
import com.unigainfo.android.meview.sample.model.ImageWithLabelViewModel;
import com.unigainfo.android.meview.sample.model.Student;
import com.unigainfo.android.meview.sample.viewholderfactory.BasicAdapter;
import com.unigainfo.android.meview.sample.viewholderfactory.HeaderViewHolderFactory;
import com.unigainfo.android.meview.sample.viewholderfactory.ImageWithLabelViewHolderFactory;
import com.unigainfo.android.meview.sample.viewholderfactory.StudentReverseViewHolderFactory;
import com.unigainfo.android.meview.sample.viewholderfactory.StudentViewHolderFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StudentViewHolderFactory.Listener, BasicAdapter.Listener, StudentReverseViewHolderFactory.Listener, ChoiceGroup.Listener {

    private RecyclerView recyclerView;
    private ChoiceGroup iconItemGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        iconItemGroup = (ChoiceGroup) findViewById(R.id.iconItemGroup);
        iconItemGroup.setListener(this);
        //initMeAdapterInstance();
        //initBasicAdapterInstances();
    }

    private void initBasicAdapterInstances() {
        BasicAdapter adapter = new BasicAdapter(this, initPureStudentList(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initMeAdapterInstance() {
        MeFlexAdapter<Object> flexibleAdapter = new MyAdapter(this);
        flexibleAdapter.registerViewHolderFactory(new StudentViewHolderFactory(this));
        flexibleAdapter.registerViewHolderFactory(new HeaderViewHolderFactory());
        flexibleAdapter.registerViewHolderFactory(new ImageWithLabelViewHolderFactory());
        flexibleAdapter.registerViewHolderFactory(new StudentReverseViewHolderFactory(this),99);

        flexibleAdapter.add(new Student("ReverseEmail@gmail.com","Apple Pen"),99);
        flexibleAdapter.addAll(initStudentList());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(flexibleAdapter);

        flexibleAdapter.clear();
    }

    private List<Student> initPureStudentList(){
        List<Student> objects = new ArrayList<>();
        for (int i = 1 ; i <= 200 ; i++){
            Student student = new Student();
            student.setName("Name Name "+i);
            student.setEmail("Name"+i+"@gmail.com");
            objects.add(student);
        }
        return objects;
    }


    private List<Object> initStudentList(){
        List<Object> objects = new ArrayList<>();
        for (int i = 1 ; i <= 20 ; i++){
            Student student = new Student();
            student.setName("Name Name "+i);
            student.setEmail("Name"+i+"@gmail.com");
            objects.add(student);
            objects.add(new DividerItem());
            if(i%10 == 0){
                HeaderViewItemModel headerModel = new HeaderViewItemModel();
                headerModel.setText("Header "+i);
                objects.add(headerModel);
            }
            if(i%2 == 0){
                ImageWithLabelViewModel model = new ImageWithLabelViewModel();
                model.setLabel("Icon "+i);
                model.setUrl("https://cdn1.iconfinder.com/data/icons/ios-7-style-metro-ui-icons/512/MetroUI_OS_Android.png");
                objects.add(model);
            }
        }
        return objects;
    }

    @Override
    public void onStudentItemClick(Student item) {
        Toast.makeText(this, "Student Name ="+item.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChoiceItemClick(View view) {
        Toast.makeText(this, "Id = "+view.getId(), Toast.LENGTH_SHORT).show();
    }
}
