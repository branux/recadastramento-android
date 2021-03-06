package com.AndroidExplorer;

import java.util.ArrayList;

import util.Constantes;

import business.Controlador;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListaImoveis extends ListActivity {
	
	MySimpleArrayAdapter enderecoList;
	ArrayList<String> listStatusImoveis;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.imoveislist);
    	this.getListView().setCacheColorHint(Color.TRANSPARENT);
    	
    	int[] colors = {0x12121212, 0xFFFFFFFF, 0x12121212}; // red for the example
    	this.getListView().setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
    	this.getListView().setDividerHeight(1);
    }
    
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);//must store the new intent unless getIntent() will return the old one.
		loadEnderecoImoveis();
	}

    private void loadEnderecoImoveis(){
    	
    	if (Controlador.getInstancia() != null){
    		if (Controlador.getInstancia().getCadastroDataManipulator() != null){
    			
    	    	listStatusImoveis = (ArrayList)Controlador.getInstancia().getCadastroDataManipulator().selectStatusImoveis(null);
    	    	ArrayList<String> listEnderecoImoveis = (ArrayList)Controlador.getInstancia().getCadastroDataManipulator().selectEnderecoImoveis(null);
    	    	
    	    	if(listEnderecoImoveis != null && listEnderecoImoveis.size() > 0){
    	        	enderecoList = new MySimpleArrayAdapter(this, listEnderecoImoveis);
    	        	setListAdapter(enderecoList);
    	        	
    	        	if (Controlador.getInstancia().getCadastroListPosition() > -1){
        	        	this.setSelection(Controlador.getInstancia().getCadastroListPosition());
        	        	enderecoList.setSelectedPosition(Controlador.getInstancia().getCadastroListPosition());
    	        	}
    	    	}
    		}
    	}
    }
    
	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {
		// user clicked a list item, make it "selected"
		enderecoList.setSelectedPosition(position);

		Controlador.getInstancia().setCadastroSelecionadoByListPosition(position);
		Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
		startActivityForResult(myIntent, 0);
	}
	
	
	public class MySimpleArrayAdapter extends ArrayAdapter<String> {
		private final Activity context;
		private final ArrayList<String> names;

		// used to keep selected position in ListView
		private int selectedPos = -1;

		public MySimpleArrayAdapter(Activity context, ArrayList<String> names) {
			super(context, R.layout.rowimovel, names);
			this.context = context;
			this.names = names;
		}

		public void setSelectedPosition(int pos){
			selectedPos = pos;
			// inform the view of this change
			notifyDataSetChanged();
		}

		public int getSelectedPosition(){
			return selectedPos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.rowimovel, null, true);

	        // change the row color based on selected state
	        if(selectedPos == position){
	        	rowView.setBackgroundColor(Color.argb(70, 255, 255, 255));
	        }else{
	        	rowView.setBackgroundColor(Color.TRANSPARENT);
	        }
	        
	        ((TextView)rowView.findViewById(R.id.nomerota)).setText(names.get(position));

			ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
			
			if ( Integer.parseInt(listStatusImoveis.get(position)) == Constantes.IMOVEL_A_SALVAR ){
				imageView.setImageResource(R.drawable.todo);
			
			} else if ( Integer.parseInt(listStatusImoveis.get(position)) == Constantes.IMOVEL_SALVO || Integer.parseInt(listStatusImoveis.get(position)) == Constantes.IMOVEL_NOVO){
				imageView.setImageResource(R.drawable.done);
			
			} else if ( Integer.parseInt(listStatusImoveis.get(position)) == Constantes.IMOVEL_SALVO_COM_ANORMALIDADE ){
				imageView.setImageResource(R.drawable.done_anormal);
			}

			return rowView;
		}
		
		public String getListElementName(int element){
			return names.get(element);
		}
	}
	
	@Override
	protected void onResume() {
    	loadEnderecoImoveis();
		super.onResume();
	}
}