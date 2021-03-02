package iutsd.android.tp2.guilleminot.myapplication;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Book est une classe simplifiée encapsulant les caractéristiques
 * de base d'un livre.
 */
public class Book implements Serializable {
    private long mBookId;
    private String mBookName=null;
    private String mAuthor=null;
    private String mDescription=null;
    private String mImageUri=null;

    /**
     * Constructeur de base initialisant seulement l'UID
     */
    public Book()
    {
        this.mBookId = GENERATE_UID();
    }

    /**
     * Constructeur de base initialisant seulement l'UID
     */
    public Book(long id)
    {
        this.mBookId = id;
    }


    /**
     * Constructeur complet initialisant tous les paramètres
     * Excepté l'image (facultative pour le début du TP)
     * @param bookId
     * @param bookName
     * @param author
     * @param desc
     */
    private Book(long bookId, String bookName, String author, String desc)
    {
        this.mBookId=bookId;
        this.mBookName=bookName;
        this.mAuthor=author;
        this.mDescription=desc;
    }

    /**
     * Copie toutes les caractéristique d'un autre livre
     * dans l'objet courant - excepté l'IUD qui reste unique
     * pour chaque instance.
     * Très utile pour mettre à jour un objet depuis une
     * instance sérialisée.
     * @param book
     */
    public void updateFromAnotherBookCopy(Book book)
    {
        this.setBookName(book.getBookName());
        this.setAuthor(book.getAuthor());
        this.setDescription(book.getDescription());
        this.setImageUri(book.getImageUri());
    }

    /**
     * Getter
     * @return book id
     */
    public long getBookId() {
        return mBookId;
    }

    /**
     * Getter
     * @return book name
     */
    public String getBookName() {
        return mBookName;
    }

    /**
     * Setter
     * @param bookName
     */
    public void setBookName(String bookName) {
        this.mBookName = bookName;
    }

    /**
     * Getter
     * @return book name
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     *
     * @param author
     */
    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    /**
     * Getter
     * @return book name
     */
    public String getDescription() {
        return mDescription;
    }
    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.mDescription = description;
    }


    /**
     * Getter
     * @return book name
     */
    public String getImageUri() {
        return mImageUri;
    }

    /**
     *
     * @param imageUri
     */
    public void setImageUri(String imageUri) {
        this.mImageUri = imageUri;
    }

    /**
     * Comparateur classique
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Book)
        {
            return ((Book)obj).getBookId()==getBookId();
        }
        return false;
    }

    /**
     * Construit un message pour partager le livre
     * par mail ou sms.
     * @param template : patron du message du type
     *                 "Un ami souhaite partager un livre avec vous,
     *                 Nom du livre : %%BOOKNAME%%,
     *                 Auteur : %%AUTHOR%%,
     *                 Description : %%DESCRIPTION%% "
     * @return
     */
    public String getShareMessage(String template)
    {
        String result = template;

        if (getBookName()!=null) {
            result = result.replace("%%BOOKNAME%%", getBookName());
        }
        else
        {
            result = result.replace("%%BOOKNAME%%", "?");

        }
        if (getAuthor()!=null) {
            result = result.replace("%%AUTHOR%%", getAuthor());
        }
        else
        {
            result = result.replace("%%AUTHOR%%", "?");

        }
        if (getDescription()!=null) {
            result = result.replace("%%DESCRIPTION%%", getDescription());
        }
        else
        {
            result = result.replace("%%DESCRIPTION%%", "?");

        }


        return result;
    }


    /**
     * SORT TYPE ENUM
     */
    public static enum SORT_ENUM
    {
        ID,
        NAME
    }

    /**
     * Methode statique permettant de générer un UID unique pour chaque livre
     * (timestamp courant)
     * @return
     */
    public static long GENERATE_UID()
    {
        return System.currentTimeMillis();
    }

    /**
     * Méthode statique permettant de trier une liste de livre
     * @param books
     * @param sortType
     */
    public static void SORT(List<Book> books, SORT_ENUM sortType)
    {
        switch (sortType)
        {
            case ID:
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book lhs, Book rhs) {
                        return (new Long(lhs.getBookId())).compareTo(new Long(rhs.getBookId()));
                    }
                });
                break;
            case NAME:
                Collections.sort(books,new Comparator<Book>() {
                    @Override
                    public int compare(Book lhs, Book rhs) {
                        return lhs.getBookName().compareTo(rhs.getBookName());
                    }
                });
                break;

        }
    }


    /**
     * Méthode statique retournant une liste de livre "Test"
     *
     * @return List<Book>
     */
    public static List<Book> GET_TEST_LIST()
    {
        Book[] books = new Book[]
                {
                        new Book(	1	,"La rencontre: l'histoire véridique...","Allan W. Eckert","Texte long Original Rencontre"),
                        new Book(	2	,"Moi, un lemming","Arkin Alan","Texte long Philo"),
                        new Book(	3	,"Mama délire, sorcière d'Afrique","Arthur Claire","Sorcière Humour"),
                        new Book(	4	,"Sales petits voleurs Ludo n° 4","Bailly, Mathy,Lapière","BD. Mélange fiction et réalité"),
                        new Book(	5	,"Pas touche à mon copain","Barbeau Philippe","Racket"),
                        new Book(	6	,"Rouge Matou","Battut Eric","Amitié  Album Personnification animaux"),
                        new Book(	7	,"C'est quoi ta collec'","Ben Kemoun Hubert","Amitié"),
                        new Book(	8	,"La citrouille olympique","Ben Kemoun Hubert","Moquerie/ enfant obèse"),
                        new Book(	9	,"Terriblement vert","Ben Kemoun Hubert","Récits parallèles"),
                        new Book(	10	,"Le serpent à fenêtre","Bobe Françoise","Album Structure Référence contes"),
                        new Book(	11	,"Le fil à retordre","Bourgeyx Claude","Détournement de mots et de concepts Nouvelles"),
                        new Book(	12	,"Avril et la poison","Branford Henrietta","Vrai roman d'aventure"),
                        new Book(	13	,"2 graines de cacao","Brisou Pellen Evelyne","Texte long Aventure "),
                        new Book(	14	,"Mon extra-terrestre préféré","Brisou-Pellen Evelyne","Science Fiction Extra-terrestres"),
                        new Book(	15	,"La montagne noire","Brouillet Christine","Récit d'aventure dans la forêt québécoise"),
                        new Book(	16	,"Les collégiens mènent l'enquête","Brouillet Christine","Policier  Langue"),
                        new Book(	17	,"Le tunnel","Browne Anthony","Album Affection"),
                        new Book(	18	,"Une histoire à 4 voix","Browne Anthony","Points de vue"),
                        new Book(	19	,"Champions, les rollers","Cahour Chantal","Récits courts de petites aventures"),
                        new Book(	20	,"Oh! les z'amoureux","Cantin Amélie et Marc","Points de vue"),
                        new Book(	21	,"Rouge Braise","Causse Rolande","Texte long Historique"),
                        new Book(	22	,"Ba","Chabas J. François","Rencontre Aventure Texte long"),
                        new Book(	23	,"Trèfle d'or","Chabat J. François","Racisme "),
                        new Book(	24	,"Le chiffre de nos jours","Chamson André","Journal"),
                        new Book(	25	,"Les machines de M. Albert","Choux Nathalie","Affection Original Inventions Album"),
                        new Book(	26	,"Little Lou","Claverie Jean","Musique USA"),
                        new Book(	27	,"Ma meilleure copine","Clément Claire","Amitié Journal"),
                        new Book(	28	,"Le temps des cerises","Clément J. Baptiste","La Commune Historique Album"),
                        new Book(	29	,"Meurtre au pays des peluches","Cohen-Scali Sarah","Enquête"),
                        new Book(	30	,"Un bisou sorcière","Cohen-Scali Sarah","Sorcière Affection Album")
                };

        return new ArrayList<Book>(Arrays.asList(books));
    }
}

