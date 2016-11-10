package br.ufc.quixada.android.flashscan;

import android.graphics.pdf.PdfDocument;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by amanda on 03/11/16.
 */
public class Documento implements Serializable{

    String caminho;
    String nome;
    Date dataCriacao;

    public Documento(String caminho, String nome, Date dataCriacao) {
        this.caminho = caminho;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
    }

    public Documento(){

    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return nome;
    }
}
