package balet.benjamin.cinephoria;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;

import balet.benjamin.cinephoria.model.TicketResponse;

public class TicketAdapter extends BaseAdapter {
    private Context context;
    private List<TicketResponse> tickets;

    private final String TAG = this.getClass().getSimpleName();

    public TicketAdapter(Context context, List<TicketResponse> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Object getItem(int position) {
        return tickets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_ticket, parent, false);
        }
        ImageView imgViewPoster = convertView.findViewById(R.id.imgViewPoster);
        TextView txtMovieTitleList = convertView.findViewById(R.id.txtMovieTitleList);
        TextView txtSessionStartEnd = convertView.findViewById(R.id.txtSessionStartEnd);
        TextView txtRoomAndSeats = convertView.findViewById(R.id.txtRoomAndSeats);

        //Afficher les infos de la séance
        TicketResponse ticket = tickets.get(position);
        txtMovieTitleList.setText(ticket.getMovieTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy HH:mm", Locale.FRENCH);
        SimpleDateFormat edf = new SimpleDateFormat("HH:mm", Locale.FRENCH);
        String startDate = sdf.format(ticket.getStartDate());
        String endDate = edf.format(ticket.getEndDate());
        txtSessionStartEnd.setText(ticket.getDay() + " - " + startDate + " - " + endDate);
        txtRoomAndSeats.setText(ticket.getRoomNumber() + " - " + ticket.getSeats());

        //Récupérer le poster du film
        Log.d(TAG, "getView: " + "https://cinephoria.jorani.org/movies/" + ticket.getImdbId() + "/poster");
        String imageUrl = "https://cinephoria.jorani.org/movies/" + ticket.getImdbId() + "/poster";
        Picasso.get().load(imageUrl).into(imgViewPoster);

        // Gestion du clic sur l'élément de la liste
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowTicketQRCodeActivity.class);
            intent.putExtra("ticketId", ticket.getTicketId());
            context.startActivity(intent);
        });

        return convertView;
    }
}
