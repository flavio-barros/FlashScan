package br.ufc.quixada.android.flashscan.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by flavio-barros on 10/11/16.
 */
public class Constantes {

    public static final String CAMINHO_EXTERNO_DOCUMENTO = Environment.getExternalStorageDirectory() +
            File.separator + "FlashScan" + File.separator + "documentos" + File.separator;

    public static final String CAMINHO_EXTERNO_IMAGENS = Environment.getExternalStorageDirectory() +
            File.separator + "FlashScan" + File.separator + "imagens" + File.separator;

    public static final String DOCUMENTO_GERADO = "Docuemtno gerado";

    public static final String RECUPERAR_DOCUMENTOS_COMPARTILHADOS = "recuperar documentos compartilhados";
}
