package com.smarttransfer.model;

/**
 * Created by jonathasalves on 26/01/2019.
 */
public class ResponseModel {

    private int code;
    private String mensagem;

    public ResponseModel(int code, String mensagem) {
        this.code = code;
        this.mensagem = mensagem;
    }

    public int getCode() { return code; }

    public void setCode(int code) { this.code = code; }

    public String getMensagem() { return mensagem; }

    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}
