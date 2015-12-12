package com.micaela.pedido;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import java.util.ArrayList;
import android.widget.Toast;
import android.content.*;
public class ProductAdapter extends BaseAdapter 
{
	protected Activity activity;
    protected ArrayList<Product> items;

    public ProductAdapter(Activity activity, ArrayList<Product> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_item_layout, null);
		}

        Product product = items.get(position);

        ImageView image = (ImageView) vi.findViewById(R.id.productImage);
        image.setImageBitmap(product.getPhoto());

        TextView name = (TextView) vi.findViewById(R.id.productName);
        name.setText(product.getName());

		TextView price = (TextView) vi.findViewById(R.id.productPrice);
        price.setText(" S/. "+product.getPrice());
		
		TextView idproduct=(TextView)vi.findViewById(R.id.productId);
		idproduct.setText(product.getIdProduct());
		idproduct.setVisibility(View.INVISIBLE);
		
		ImageButton buttonAdd = (ImageButton) vi.findViewById(R.id.productAddCart);
        buttonAdd.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					View vi =(View)v.getParent();
					TextView id=(TextView)vi.findViewById(R.id.productId);
					
					Intent itemOrder= new Intent(activity.getApplicationContext(), ItemOrderActivity.class);
					itemOrder.putExtra("idProduct",id.getText());
					activity.startActivityForResult(itemOrder, 1);
					
				}
			});
			
			
        return vi;
    }

}
