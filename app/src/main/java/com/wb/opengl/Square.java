package com.wb.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @Author WangBu
 * @DATE 20-3-18 14:39
 * E-Mail Address：android_wb@163.com
 */
public class Square {
    //顶点坐标
    private float[] vertices = {
            -1.0f,1.0f,0f, //左上
            -1.0f,-1.0f,0f,//左下
            1.0f,-1.0f,0f,//右下
            1.0f,1.0f,0f,//右上
    };
    //链接顺序下标
    private short[] indices = {0,1,2,0,2,3};
    //定点数据缓冲区
    private FloatBuffer vertexBuffer;
    //顺序缓冲器
    private ShortBuffer indexBuffer;

    Square(){
        //一个float四个Byte
        ByteBuffer vbb  = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length*2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    public void draw(GL10 gl){
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES,indices.length,GL10.GL_UNSIGNED_SHORT,indexBuffer);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
