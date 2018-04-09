package com.example.sassassaisn.shortpath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sassassaisn.shortpath.Dijkstra2.Edge;
import com.example.sassassaisn.shortpath.Dijkstra2.Vertex;

import java.util.List;

public class MainActivity extends Activity {

    private Button btn_ok;  //路径规划
    private Button btn_add;  //添加点
    private Button btn_reset;  //重置数据
    private EditText et_point;   //输入框
    private TextView tv_point;   //输出文字点 路径 距离
    private String path = "";   //路径
    private String point[] = new String[9];   //点数组
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        et_point = (EditText) findViewById(R.id.et_point);
        tv_point = (TextView) findViewById(R.id.textView1);

        //从edit text中获取起始点和终点
        btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_point.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this,"请先添加起点然后再添加终点",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(i == 0){
                    btn_add.setText("请设置终点");
                }
                if (i == 1) {
                    btn_add.setClickable(false);
                    btn_add.setText("已设置完毕");
                }
                point[i] = et_point.getText().toString().trim();
                tv_point.setText(tv_point.getText().toString() + point[i]
                        + ",");
                i++;
                et_point.setText("");  //清空编辑框
            }
        });

        //规划最短路径
        btn_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 1; j < i; j++) {
                    path += dijkstra(point[j - 1], point[j]);
                }
                //显示结果
                tv_point.setText(path);
                //禁止按钮
                btn_ok.setClickable(false);
            }
        });

        //重置按钮被单击
        btn_reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
    }

    public String dijkstra(String p1, String p2) {
        // 构建有向图
        // 建点
        Vertex v1 = new Vertex("a");
        Vertex v2 = new Vertex("b");
        Vertex v3 = new Vertex("c");
        Vertex v4 = new Vertex("d");
        Vertex v5 = new Vertex("e");

        //建边
        List<Edge> e1l = v1.adj;
        List<Edge> e2l = v2.adj;
        List<Edge> e3l = v3.adj;
        List<Edge> e4l = v4.adj;
        List<Edge> e5l = v5.adj;

        //赋值边
        //a点
        Edge e12 = new Edge(v2, 45);
        Edge e13 = new Edge(v3, 30);
        Edge e15 = new Edge(v5, 50);
        e1l.add(e12);
        e1l.add(e13);
        e1l.add(e15);
        //b点
        Edge e24 = new Edge(v4, 60);
        Edge e21 = new Edge(v1, 45);
        e2l.add(e24);
        e2l.add(e21);
        //c点
        Edge e35 = new Edge(v5, 45);
        Edge e31 = new Edge(v1, 30);
        e3l.add(e35);
        e3l.add(e31);
        //d点
        Edge e45 = new Edge(v5, 55);
        Edge e42 = new Edge(v2, 60);
        e4l.add(e45);
        e4l.add(e42);
        //e点
        Edge e51=new Edge(v1, 50);
        Edge e53=new Edge(v3, 45);
        Edge e54=new Edge(v4, 55);
        e5l.add(e51);
        e5l.add(e54);
        e5l.add(e53);
        // 构建有向图完毕

        Dijkstra2.vertexMap.put("a", v1);
        Dijkstra2.vertexMap.put("b", v2);
        Dijkstra2.vertexMap.put("c", v3);
        Dijkstra2.vertexMap.put("d", v4);
        Dijkstra2.vertexMap.put("e", v5);
        
        //返回两点的最短路径
        return Dijkstra2.dijkstra(p1, p2);
    }

}
