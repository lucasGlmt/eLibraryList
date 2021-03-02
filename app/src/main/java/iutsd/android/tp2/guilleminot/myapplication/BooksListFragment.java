package iutsd.android.tp2.guilleminot.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iutsd.android.tp2.guilleminot.myapplication.Book;

/**
 * Fragment permettant l'affichage d'une liste de livre
 * avec 3 boutons par item (details, actions, modifier)
 *
 * IMPORTANT : L'activité qui intègre ce fragment doit
 * implémenter les interfaces suivantes :
 * - BooksListFragment.OnListFragmentInteractionListener : pour que l'activité
 * soit notifiée lors du click sur un bouton (principe de
 * délégation des actions vu en cours)
 * - BooksListFragment.BooksListProvider : pour que ce fragment puisse récupérer
 * la liste de livres depuis l'activité
 */
public class BooksListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private List<Book> mBooksList=new ArrayList<>();
    private Book.SORT_ENUM sort = Book.SORT_ENUM.ID;


    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    private BooksListProvider mBooksListProvider;

    /**
     * Constructeur obligatoire pour l'instantiation par le fragment manager
     */
    public BooksListFragment() {
    }


    /**
     * Création de la vue et donc de la liste (utilise un RecyclerView)
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la vue
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books_list, container, false);

        // mise en place de l'adapter du RecyclerView : MyBookItemRecyclerViewAdapter (similaire à un BaseAdapter)
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mRecyclerView.setAdapter(new MyBookItemRecyclerViewAdapter(mBooksList, mListener));
        }

        //récupération de l'état du tri (sauvegardé dans le onSaveInstanceState)
        if (savedInstanceState!=null)
        {
            this.sort = (Book.SORT_ENUM)savedInstanceState.getSerializable("Sort");

        }

        //effectue la mise à jour de la liste (actualisation des livres si changements)
        update();

        return view;
    }

    /**
     * Sauvegarde l'état du fragment (pour le changement d'orientation de la tablette, par exemple,
     * puisque l'activité parente est recréée, le fragment l'est aussi)
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        // Save the user's current game state
        savedInstanceState.putSerializable("Sort", sort);
    }


    /**
     * Le callback onAttach est appelé, comme son nom l'indique, au moment où le fragment est
     * attaché à l'activité. C'est donc dans cette méthode que nous allons définir notre activité
     * parente comme étant "listener" des actions (OnListFragmentInteractionListener) ainsi que
     * fournisseur de livres (BooksListProvider)
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

        if (context instanceof BooksListProvider) {
            mBooksListProvider = ((BooksListProvider) context);

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BooksListProvider interface");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mBooksListProvider = null;
    }

    /**
     * Methode permettant le tri des livre soit par nom, soit par id (cf. Book.SORT_ENUM)
     * @param sort
     */
    public void sortList(Book.SORT_ENUM sort)
    {
        this.sort=sort;

        update();
    }


    /**
     * méthode permettant d'effectuer la mise à jour de la liste de livres
     */
    public void update()
    {
        mBooksList.clear();
        mBooksList.addAll(mBooksListProvider.getBooksList());
        Book.SORT(mBooksList, sort);


        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.invalidate();
    }








    /**
     *  Interface définissant la listes de actions pour lesquelles l'activité parentes sera notifiée
     * en tant que OnListFragmentInteractionListener
     */
    public interface OnListFragmentInteractionListener {
        void onClickBookDetails(Book book);
        void onClickBookModify(Book book);
        void onClickBookActions(Book book);
    }

    /**
     * Interface définissant les méthodes (la méthode ici) que l'activité parente devra implémenté
     * en tant que BooksListProvider
     */
    public interface BooksListProvider {
        List<Book> getBooksList();
    }


    /**
     * Ce RecyclerViewAdapter, à l'instar d'un Base Adapter ou d'un ArrayAdapter, fait la jonction
     * entre les données (ici les livres) et leur affichage dans la liste (RecyclerView)
     */
    private class MyBookItemRecyclerViewAdapter extends RecyclerView.Adapter<MyBookItemRecyclerViewAdapter.ViewHolder> {

        private final List<Book> mValues;
        private final OnListFragmentInteractionListener mListener;

        /**
         * Constructeur de l'adapter
         * @param items : la liste de livres
         * @param listener : le listener des actions
         */
        public MyBookItemRecyclerViewAdapter(List<Book> items, OnListFragmentInteractionListener listener) {
            mValues = items;
            mListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /* Ici nous créons la vue "generique" pour un item de la liste */

            //TODO : déserialisation (inflate) de la vue du fragment "fragment_bookitem""
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bookitem, parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            /* Ici nous "bindons / associons" la vue avec le livre à la position donnée en paramètre
            (un peu l'équivalent du getView d'un adapter) au travers d'un ViewHolder (cf classe ViewHolder) */
            holder.mItem = mValues.get(position);
            holder.mBookTitle.setText(mValues.get(position).getBookName());

            // TODO : Gérer les actions "setOnClickListener" pour les 3 boutons en déléguant les actions via le Listener mListener
            holder.mActionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickBookActions(mBooksList.get(position));
                }
            });

            holder.mModifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickBookModify(mBooksList.get(position));
                }
            });

            holder.mDetailsButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mListener.onClickBookDetails(mBooksList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            // TODO
            return mBooksList.size();
        }

        /**
         * Le ViewHolder qui doit contenir les éléments à "binder / associer" avec chaque livre
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mBookTitle;
            public final ImageButton mDetailsButton;
            public final ImageButton mModifyButton;
            public final ImageButton mActionsButton;
            public Book mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mBookTitle = (TextView) view.findViewById(R.id.titleTextView);
                // TODO : Associer / Affecter les 3 boutons
                mDetailsButton = view.findViewById(R.id.detailsButton);
                mModifyButton = view.findViewById(R.id.modifyButton);
                mActionsButton = view.findViewById(R.id.actionButton);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mBookTitle.getText() + "'";
            }
        }
    }

}
