package ni.org.ics.estudio.zen.appmovil.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.org.ics.estudio.zen.appmovil.R;
import ni.org.ics.estudio.zen.appmovil.domain.ParticipanteZen;

import java.text.SimpleDateFormat;
import java.util.List;

public class ParticipanteAdapter extends ArrayAdapter<ParticipanteZen> {

	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public ParticipanteAdapter(Context context, int textViewResourceId,
                          List<ParticipanteZen> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.complex_list_item, null);
		}
        ParticipanteZen p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.code) + ": " + p.getCodigo());
			}
			
			textView = (TextView) v.findViewById(R.id.der_text);
			if (textView != null) {
				textView.setText(mDateFormat.format(p.getFechaNac()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			String nameCompleto = p.getNombre1();
			if (p.getNombre2()!=null) nameCompleto = nameCompleto + " "+  p.getNombre2();
			nameCompleto = nameCompleto +" "+ p.getApellido1();
			if (p.getApellido2()!=null) nameCompleto = nameCompleto + " "+  p.getApellido2();
			if (textView != null) {
				textView.setText(nameCompleto);
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (p.getSexo().equals("M")) {
                    imageView.setImageResource(R.drawable.male);
                } else {
                    imageView.setImageResource(R.drawable.female);
                }
			}
            String labelHeader="";
            labelHeader = labelHeader + "Tutor: " + p.getTutor()+"<br />";
            labelHeader = labelHeader + "Parentesco: " + p.getRelacionFamTutor()+"<br />";
            labelHeader = labelHeader + "Estudios: " + p.getEstudios()+"<br />";
            labelHeader = labelHeader + "Código Casa: " + p.getCodigoCasa()+"<br />";
            labelHeader = labelHeader + "Manzana: " + p.getManzana()+"<br />";
            labelHeader = labelHeader + "Barrio: " + p.getNombreBarrio()+"<br />";
            labelHeader = labelHeader + "Dirección: " + p.getDireccion()+"<br />";
            labelHeader = labelHeader + "------------<br />";
            if (p.getEstPart().equals(0)){
                labelHeader = labelHeader + "<br /><font color='red'>Participante retirado</font>";
            }
            textView = (TextView) v.findViewById(R.id.infoc_text);
            if (textView != null) {
                textView.setText(Html.fromHtml(labelHeader));
            }
		}
		return v;
	}
}
