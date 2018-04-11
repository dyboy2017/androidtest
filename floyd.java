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

    //控件
    private EditText edit_input;
    private Button btn_add,btn_design,btn_reset;
    private TextView tv_result;

    //参数
    public int len = 5;   //图中点的个数
    public static int INF = Integer.MAX_VALUE;  //定义INF为无限大
    public String path = "";  //最短路径
    public char[] point = new char[2];  //存放起始点
    public int flag = 0;  //保存判断输入状态
    public int[][] Matrix = {       //邻接矩阵存放图
            {0,45,30,INF,50},
            {45,0,INF,60,INF},
            {30,INF,0,INF,45},
            {INF,60,INF,0,55},
            {50,INF,45,55,0}
    };
    public int[][] path_matrix = new int[len][len]; //存放最短路径的矩阵


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //输入框
        edit_input = findViewById(R.id.edit_input);

        //结果框
        tv_result = findViewById(R.id.tv_result);

        //添加按钮
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit_input.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(flag == 0){
                    edit_input.setHint("请输入终点，例如：b");
                }
                if(flag == 1){
                    btn_add.setText("已输入完毕");
                    btn_add.setClickable(false);
                }
                //获取编辑框的信息
                String tmp = edit_input.getText().toString().trim();
                point[flag] = tmp.charAt(0);
                flag++;
                edit_input.setText("");
            }
        });

        //路径规划
        btn_design = findViewById(R.id.btn_design);
        btn_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取最短路径的长度
                int length = floyd_length(point[0],point[1]);

                //获取路径
                int start = (int)point[0]-97;
                int end = (int)point[1]-97;
                Toast.makeText(MainActivity.this,Integer.toString(end),Toast.LENGTH_SHORT).show();
                getPath(start,end);

                //输出到textView
                tv_result.setText("起点："+point[0]+"---->"+point[1]+"的最短距离为："+Integer.toString(length)+"\n最短路径为："+point[0]+path);

            }
        });

        //重置页面
        btn_reset = findViewById(R.id.btn_reload);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }

    //Floyd算法 返回最短路径的长度
    public int floyd_length(char start_point,char end_point){

        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                for(int k=0;k<5;k++){
                    if((Matrix[i][k]<INF && Matrix[k][j]<INF) && (Matrix[i][j]>Matrix[i][k]+Matrix[k][j])){
                        Matrix[i][j] = Matrix[i][k]+Matrix[k][j];
                        path_matrix[i][j] = k;
                    }
                }
            }
        }
        return Matrix[(int)start_point-97][(int)end_point-97];
    }


    //返回最短路径
    public void getPath(int i,int j){
        if(i == j) return;
        if(path_matrix[i][j] == 0){
            char tmp = (char)(97+j);
            path = path + "--->" + tmp;
        }
        else{
            getPath(i,path_matrix[i][j]);
            getPath(path_matrix[i][j],j);
        }
    }




}
