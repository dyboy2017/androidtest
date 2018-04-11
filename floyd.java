package com.example.sassassaisn.shortestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText ed_input;
    private Button btn_add,btn_design,btn_reload;
    private TextView tv_result;
    public String path = "";  //最短路径
    private int flag = 0; //判断输入flag
    private char point[] = new char[2];  //点数组
    public int INF = Integer.MAX_VALUE;  //定义INF 为 无穷大
    public int[][] Matrix ={  //邻接矩阵
            {0,45,30,INF,50},
            {45,0,INF,60,INF},
            {30,INF,0,INF,45},
            {INF,60,INF,0,55},
            {50,INF,45,55,0}
    };
    public int[][] path_matrix = new int[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //输入框
        ed_input = findViewById(R.id.edit_input);

        //输出的TextView
        tv_result = findViewById(R.id.tv_result);

        //添加始末点
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //....
                if(ed_input.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                    return;
                }
                String temp_input = ed_input.getText().toString().trim();
                point[flag] = temp_input.charAt(0);
                if( flag == 0){
                    ed_input.setHint("请输入终点，例如：b");
                }
                if( flag == 1){
                    btn_add.setClickable(false);
                    btn_add.setText("已输入完毕");
                }
                System.out.println("point:"+point[flag]);
                flag++;
                ed_input.setText("");
            }
        });

        //规划路线
        btn_design = findViewById(R.id.btn_design);
        btn_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int length = folyed_length(point[0],point[1]);

                //输出路径
                int start = (int)point[0]-97;
                int end = (int)point[1]-97;
                Toast.makeText(MainActivity.this,Integer.toString(end),Toast.LENGTH_SHORT).show();
                getPath(start,end);
                path = point[0] + path;
                tv_result.setText("起点"+ point[0] + "---> 终点" + point[1] + "  的距离为：" + length+"\n最短路径为："+path);
            }
        });

        //重置页面数据
        btn_reload = findViewById(R.id.btn_reload);
        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //...
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }


    public int folyed_length(char start_point,char end_point){
        //Floyd返回最短路径的长度

        for(int i=0;i<5;i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if ((Matrix[i][k] < INF && Matrix[k][j] < INF) && (Matrix[i][j] > Matrix[i][k] + Matrix[k][j])) {
                        Matrix[i][j] = Matrix[i][k] + Matrix[k][j];
                        path_matrix[i][j] = k;
                    }
                }
            }
        }
        return Matrix[(int)start_point-97][(int)end_point-97];
    }


    public void getPath(int i ,int j){
        if(i==j) return;
        if(path_matrix[i][j]==0) {
            char temp=(char)(97+j);
            path=path + "-->" +temp;
        }
        else{
            getPath(i,path_matrix[i][j]);
            getPath(path_matrix[i][j],j);
        }
    }
}