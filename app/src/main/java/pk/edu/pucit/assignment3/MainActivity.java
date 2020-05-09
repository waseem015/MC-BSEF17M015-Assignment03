package pk.edu.pucit.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    try {
        String json="";
        InputStream is=getResources().openRawResource(R.raw.data);
        byte[] data=new byte[is.available()];
        while(is.read(data)!=-1) {
            //empty automatically reads in data
        }
        json=new String(data);
        try {
            JSONObject b=new JSONObject(json);
            JSONArray books=b.getJSONArray("books");
            int l=books.length();
            String[] title=new String[l];
            String[] level=new String[l];
            String[] info=new String[l];
            String[] image=new String[l];
            String[] url=new String[l];
            for(int i=0;i<books.length();i++)
            {
                JSONObject book=books.getJSONObject(i);
                title[i]=book.getString("title");
                level[i]=book.getString("level");
                info[i]=book.getString("info");
                url[i]=book.getString("url");
                image[i]=book.getString("cover");
            }
            RecyclerView rv=findViewById(R.id.rv);

            LinearLayoutManager llm=new LinearLayoutManager(this);
            rv.setLayoutManager(llm);
            RVAdapter adapter=new RVAdapter(this,title,level,info,image,url);
            rv.setAdapter(adapter);

        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
    catch (IOException ex) {
        ex.printStackTrace();
    }

    }
}
