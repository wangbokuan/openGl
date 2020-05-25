package com.wb.opengl;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author WangBu
 * @DATE 20-3-17 14:39
 * E-Mail Address：android_wb@163.com
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mGLSurfaceView = findViewById(R.id.glv);
        mGLSurfaceView.setOnClickListener(this);
        mGLSurfaceView.setRenderer(new OpenGlRender());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.glv:
                startActivity(new Intent(this,CameraActivity.class));
                break;
            default:
                break;
        }
    }


    class OpenGlRender implements GLSurfaceView.Renderer {
        Square square = new Square();

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
            gl.glShadeModel(GL10.GL_SMOOTH);
            gl.glClearDepthf(1.0f);
            gl.glEnable(GL10.GL_DEPTH_TEST);
            gl.glDepthFunc(GL10.GL_LEQUAL);
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int width, int height) {
            gl10.glViewport(0, 0, width, height);
            gl10.glMatrixMode(GL10.GL_PROJECTION);
            gl10.glLoadIdentity();
            GLU.gluPerspective(gl10, 45.0f, (float) width / (float) height, 0.1f, 100f);
            gl10.glMatrixMode(GL10.GL_MODELVIEW);
            gl10.glLoadIdentity();

        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl10.glLoadIdentity();
            gl10.glTranslatef(0, 0, -3f);
            square.draw(gl10);


        }
    }
}
