package com.anuj.artisitify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    DataHelper myDB;
    String hint =" ";

    String[] images = {"https://www.pablopicasso.org/images/paintings/three-musicians.jpg",
            "http://www.themost10.com/wp-content/uploads/2012/03/Blue-Nude-By-Pablo-Picasso.jpg",
            "http://thirddime.com/media/1/salvador-dali/salvador-dali_the-elephants_thirddime.jpg",
            "http://www.themost10.com/wp-content/uploads/2012/03/03-Asleep.jpg"
            ,"https://www.dalipaintings.com/images/paintings/the-burning-giraffe.jpg"
    };

    String[] names = {"Pablo Picasso", "Pablo Picasso","Salvador Dali","Pablo Picasso","Salvador Dali"};

    String[] des = {"Three Musicians"," Blue Nude ","The Elephants","Asleep","The Burning Giraffe"};

    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DataHelper(this);

        ListView listView = (ListView) findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getData();

                if (res.getCount() ==0 )
                {
                    Toast.makeText(getApplicationContext(),"No data present", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(res.getString(0)+"\n");
                    SharedPreferences cd = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    cd.edit().putString("info", res.getString(0)).apply();
                    boolean isUpdte = myDB.UpdateData(res.getString(0));
                }

                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();




                Intent i = new Intent(MainActivity.this,Favourites.class);
                startActivity(i);


                //myDB.delData();
            }
        });

    }

    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
           convertView = getLayoutInflater().inflate(R.layout.customlayout,null);
            pos = position;

            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview);
            TextView textView = (TextView)convertView.findViewById(R.id.text);
            TextView text = (TextView)convertView.findViewById(R.id.text2);
            Button fav = (Button)convertView.findViewById(R.id.button);

            Picasso.with(getApplicationContext())
                    .load(images[position])
                    .into(imageView);
           // imageView.setImageResource(images[position]);
            textView.setText(names[position]);
            text.setText(des[position]);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = myDB.insertData(hint);


                    switch (position){

                        case 0: hint = "0";
                           // Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                            break;

                        case 1 :hint = "1";
                            //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:hint = "2";
                            //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                            break;

                        case 3: hint = "3";
                            //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                            break;

                        case 4: hint = "4";
                            //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                            break;
                    }


                    if (isInserted == true ){
                            Toast.makeText(getApplicationContext(), " Data Inserted Successfully ", Toast.LENGTH_SHORT).show();
                    }

                    else
                        Toast.makeText(getApplicationContext()," Failed", Toast.LENGTH_SHORT).show();

                }
            });

            return convertView;
        }
    }
}
