package br.ufc.quixada.android.flashscan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;

import br.ufc.quixada.android.flashscan.util.Constantes;

public class ActivityCapturarImagem extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 10;
    Button btnVoltar;
    Button btnGerarPdf;
    ImageView imgCamera;
    File imagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctivity_capturar_imagem);

        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnGerarPdf = (Button) findViewById(R.id.btnGerarPdf);
        imgCamera = (ImageView) findViewById(R.id.imgCamera);

        imagem = pegarArquivo();

        abrirCamera();
    }

    public File pegarArquivo(){
        File pastaExterna = new File(Constantes.CAMINHO_EXTERNO_IMAGENS);

        if(!pastaExterna.exists()){
            pastaExterna.mkdirs();
        }

        String nomeArquivo = new Date().toString()+".PNG";

        return new File(pastaExterna, nomeArquivo);
    }

    public void abrirCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagem));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    public void btnVoltarClicked(View view){
        irTelaPrincipal();
    }

    public void irTelaPrincipal(){
        Intent intent = new Intent(this, ActivityPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            imgCamera.setImageDrawable(Drawable.createFromPath(imagem.getPath()));
        }else{
            irTelaPrincipal();
        }
    }

    public void btnGerarPdfClicked(View view){
        irTelaGerarPdf();
    }

    public void irTelaGerarPdf(){
        Intent intent = new Intent(this, ActivityGerarPdf.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("imagem", imagem.getPath());
        startActivity(intent);
    }
}
