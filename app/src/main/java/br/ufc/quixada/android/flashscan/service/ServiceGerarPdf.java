package br.ufc.quixada.android.flashscan.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

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

import br.ufc.quixada.android.flashscan.Documento;
import br.ufc.quixada.android.flashscan.util.Constantes;

/**
 * Created by flavio-barros on 09/11/16.
 */
public class ServiceGerarPdf extends IntentService {

    String caminhoImagem;
    String nomeArquivoPdf;
    String caminhoArquivoPdf;
    double latitude;
    double longitude;

    public ServiceGerarPdf() {
        super(ServiceGerarPdf.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        caminhoImagem = intent.getExtras().getString("caminhoImagem");
        nomeArquivoPdf = intent.getExtras().getString("nomeArquivoPdf");
        latitude = intent.getExtras().getDouble("latitude");
        longitude = intent.getExtras().getDouble("longitude");

        Log.d("TESTE", String.valueOf(latitude));
        Log.d("TESTE", String.valueOf(longitude));

        gerarPDF();
    }

    public void gerarPDF(){

        Document document = new Document();
        File pastaExterna = new File(Constantes.CAMINHO_EXTERNO_DOCUMENTO);

        if(!pastaExterna.exists()){
            pastaExterna.mkdirs();
        }

        String nomeArquivo = nomeArquivoPdf;
        File arquivoPdf = new File(pastaExterna, nomeArquivo);
        caminhoArquivoPdf = arquivoPdf.getPath();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(caminhoImagem);
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(arquivoPdf.getPath()));
            document.open();
            Image image = Image.getInstance (stream.toByteArray());
            image.setAlignment(Image.MIDDLE);
            image.setBorder(Image.BOX);
            image.setBorderWidth(0);
            document.add(image);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        enviarBroadcastDocumentoGerado();
    }

    public void enviarBroadcastDocumentoGerado(){
        Intent intent = new Intent(Constantes.DOCUMENTO_GERADO);

        Documento documento = new Documento();
        documento.setCaminho(caminhoArquivoPdf);
        documento.setNome(nomeArquivoPdf);
        documento.setDataCriacao(new Date());
        documento.setLatitude(latitude);
        documento.setLongitude(longitude);

        intent.putExtra("documento", documento);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
