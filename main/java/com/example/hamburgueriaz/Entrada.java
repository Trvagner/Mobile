package com.example.hamburgueriaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Entrada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);
    }

    private int quantidade = 0;
    private boolean temBacon = false;
    private boolean temQueijo = false;
    private boolean temOnionRings = false;

    public void somar(View view) {
        quantidade++;
        atualizarQuantidade();
    }

    public void subtrair(View view) {
        if(quantidade > 0){
            quantidade--;
            atualizarQuantidade();
        }
    }

    public void atualizarQuantidade() {
        TextView quantidadeView = (TextView) findViewById(R.id.quantidade);

        quantidadeView.setText(String.valueOf(quantidade));
    }

    public void enviarPedido(View view) {
        EditText nomeClienteView = (EditText) findViewById(R.id.nome);
                String nomeCliente = nomeClienteView.getText().toString();

        CheckBox baconView = (CheckBox) findViewById(R.id.bacon);
        temBacon = baconView.isChecked();

        CheckBox queijoView = (CheckBox) findViewById(R.id.queijo);
        temQueijo = queijoView.isChecked();

        CheckBox onionRingsView = (CheckBox) findViewById(R.id.onion);
        temOnionRings = onionRingsView.isChecked();

        double precoFinal = calcularPreco();

        String resumoPedido = criarResumoPedido(nomeCliente, temBacon, temQueijo, temOnionRings, quantidade, precoFinal);
        TextView resumoPedidoView = (TextView) findViewById(R.id.resumoPedido);

        resumoPedidoView.setText(resumoPedido);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nomeCliente);
        intent.putExtra(Intent.EXTRA_TEXT, resumoPedido);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);}

    }

    public double calcularPreco() {
        double precoBase = 20.0;
        if(temBacon) precoBase += 2.0;
        if (temQueijo) precoBase += 2.0;
        if (temOnionRings) precoBase += 3.0;
        return precoBase * quantidade;
    }

    public String criarResumoPedido (String nomeCliente, boolean temBacon, boolean temQueijo,boolean temOnionRings, int quantidade, double precoFinal) {
        return "Nome do cliente:" + nomeCliente + "\n" +
                "Tem Bacon?" + (temBacon ? " Sim" : " Não") + "\n" +
                "Tem Queijo?" + (temQueijo ? " Sim" : " Não") + "\n" +
                "Tem OnionRings?" + (temOnionRings ? " Sim" : " Não") + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Preço Final: R$ " + String.format("%.2f", precoFinal);
    }
}