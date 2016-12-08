package br.ufc.quixada.android.flashscan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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

public class ActivityGerarPdf extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String imagem;
    Button btnGerarPDF;
    EditText txtNomeArquivo;
    String nomeImagem;
    File filePDF;
    double latitude;
    double longitude;
    Location location;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_pdf);

        Intent intent = getIntent();
        imagem = intent.getStringExtra("imagem");

        btnGerarPDF = (Button) findViewById(R.id.btnGerarPdf);
        txtNomeArquivo = (EditText) findViewById(R.id.txtNomeArquivo);

        buildGoogleApiClient();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
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
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        ActivityPrincipal.msgRecebida = true;
        startService(intent);

        irTelaPrincipal();
    }

    @Override
    public void onConnected(Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
