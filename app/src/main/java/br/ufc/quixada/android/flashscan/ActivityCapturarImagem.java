package br.ufc.quixada.android.flashscan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityCapturarImagem extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 10;
    Button btnVoltar;
    Button btnGerarPdf;
    ImageView imgCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctivity_capturar_imagem);

        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnGerarPdf = (Button) findViewById(R.id.btnGerarPdf);
        imgCamera = (ImageView) findViewById(R.id.imgCamera);

        abrirCamera();

    }

    public void abrirCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    public void btnVoltarClicked(View view){
        irTelaPrincipal();
    }

    protected void irTelaPrincipal(){
        Intent intent = new Intent(this, ActivityPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                imgCamera.setImageBitmap(cameraImage);
            }
        }

    }
}
