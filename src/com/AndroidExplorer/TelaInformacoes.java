package com.AndroidExplorer;

import java.util.List;

import business.Controlador;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class TelaInformacoes extends FragmentActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.roteiroinfo);
    	
    	List<String> infoList = Controlador.getInstancia().getCadastroDataManipulator().selectInformacoesRota();
    	String anoMesReferencia = infoList.get(4).substring(4, 6) + "/" + infoList.get(4).substring(0, 4);
    	
    	((TextView)findViewById(R.id.valorGrupo)).setText(infoList.get(0));
    	((TextView)findViewById(R.id.valorLocalidade)).setText(infoList.get(1));
    	((TextView)findViewById(R.id.valorSetor)).setText(infoList.get(2));
    	((TextView)findViewById(R.id.valorRota)).setText(infoList.get(3));
    	((TextView)findViewById(R.id.valorAnoMesReferencia)).setText(anoMesReferencia);
    	((TextView)findViewById(R.id.valorTotalImoveis)).setText(String.valueOf(Controlador.getInstancia().getCadastroDataManipulator().getNumeroImoveis()));
    	((TextView)findViewById(R.id.valorUsuario)).setText(infoList.get(5));
    }
    
    
}