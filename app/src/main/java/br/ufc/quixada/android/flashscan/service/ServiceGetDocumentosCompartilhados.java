package br.ufc.quixada.android.flashscan.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufc.quixada.android.flashscan.Documento;
import br.ufc.quixada.android.flashscan.util.Constantes;

/**
 * Created by flavio-barros on 11/11/16.
 */
public class ServiceGetDocumentosCompartilhados extends IntentService {

    public ServiceGetDocumentosCompartilhados(){
        super(ServiceGetDocumentosCompartilhados.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("Serviço", "Serviço executando 1");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("Serviço", "Serviço executando 2");

        enviarBroadcastDocumentoCompartilhados();
    }

    public void enviarBroadcastDocumentoCompartilhados(){
        Intent intent = new Intent(Constantes.RECUPERAR_DOCUMENTOS_COMPARTILHADOS);

        Documento documento1 = new Documento();
        documento1.setCaminho(Constantes.CAMINHO_EXTERNO_DOCUMENTO+"copo.PDF");
        documento1.setNome("copo.PDF");
        documento1.setDataCriacao(new Date());

        Documento documento2 = new Documento();
        documento2.setCaminho(Constantes.CAMINHO_EXTERNO_DOCUMENTO+"tela.PDF");
        documento2.setNome("tela.PDF");
        documento2.setDataCriacao(new Date());

        intent.putExtra("documento1", documento1);
        intent.putExtra("documento2", documento2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
