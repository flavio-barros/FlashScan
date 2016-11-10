package br.ufc.quixada.android.flashscan.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by flavio-barros on 09/11/16.
 */
public class GerarPdfService extends IntentService {

    public GerarPdfService(){
        super(GerarPdfService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        String nome
    }
}
