package br.ufc.quixada.android.flashscan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import br.ufc.quixada.android.flashscan.service.ServiceGerarPdf;
import br.ufc.quixada.android.flashscan.util.Constantes;

public class ActivityGerarPdf extends AppCompatActivity {

    String imagem;
    Button btnGerarPDF;
    EditText txtNomeArquivo;
    String nomeImagem;
    File filePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_pdf);

        Intent intent = getIntent();
        imagem = intent.getStringExtra("imagem");

        btnGerarPDF = (Button) findViewById(R.id.btnGerarPdf);
        txtNomeArquivo = (EditText) findViewById(R.id.txtNomeArquivo);

    }

    public void irTelaPrincipal(){
        Intent intent = new Intent(this, ActivityPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    public void btnSalvarPDFClicked(View view){
        nomeImagem = txtNomeArquivo.getText().toString();
        gerarPDF();
    }


    public void gerarPDF(){

        String nomeArquivo = nomeImagem+".PDF";

        Intent intent = new Intent(this, ServiceGerarPdf.class);
        intent.putExtra("caminhoImagem", imagem);
        intent.putExtra("nomeArquivoPdf", nomeArquivo);
        ActivityPrincipal.msgRecebida = true;
        startService(intent);

        irTelaPrincipal();
    }
}
