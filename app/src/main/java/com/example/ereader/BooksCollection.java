package com.example.ereader;

import java.util.ArrayList;
import java.util.List;

public class BooksCollection {
    private static final BooksCollection bc = new BooksCollection();
    public ArrayList<Book> books =  new ArrayList<>();
    public ArrayList<BookDownload> booksDownload =  new ArrayList<>();

    public BooksCollection(){
    }

    public static BooksCollection getBooksCollections(){
        return bc;
    }

    public void add(Book book){
        this.books.add(book);
    }
    public void add(BookDownload book){
        this.booksDownload.add(book);
    }

    public List<Book> getAll(){
        return this.books;
    }
    public List<BookDownload> getAll(BookDownload bk){return this.booksDownload; }

    public int size(){
        return this.books.size();
    }
    public int size(BookDownload bk){return this.booksDownload.size();}

    public Book get(int index,Book bk){
        return this.books.get(index);
    }
    public Book get(int index,BookDownload bk){ return this.books.get(index); }


    {
        books.add(new Book(1,"Капитанская дочка", "А.С. Пушкин", "На страницах произведения Маша, дочь капитана Миронова, появляется миловидной девушкой «лет осьмнадцати». Она добрая, робкая, застенчивая, легко краснеет. Мать считает ее трусихой. Капитанская дочка глубоко порядочна, немногословна, умна и хорошо разбирается в людях. Она отказывает сватающемуся к ней Швабрину, так как видит его насквозь.", 4.5));
        books.add(new Book(2,"Ревизор", "Н.В. Гоголь", "«Ревизор» — комедия в пяти действиях русского писателя Николая Васильевича Гоголя. Годом написания считается 1835 год, однако окончательные правки в своё произведение Н. В. Гоголь внёс в 1842 году.", 4.2,R.drawable.monk3));
        books.add(new Book(3,"Отрочество", "Л.Н. Толстой", "Вторая повесть в псевдо-автобиографической трилогии Льва Николаевича Толстого впервые напечатана в 1854 году в журнале «Современник».", 4.8));
        books.add(new Book(4,"Ася", "Н.Н. Тургенев", "Повесть Ивана Сергеевича Тургенева. Написана в 1857 году, опубликована в 1858 году в первом номере журнала «Современник».", 4.0,R.drawable.monk1));
        books.add(new Book(5,"Горе от ума", "А.С. Грибоедов", "Комедия в стихах Александра Сергеевича Грибоедова. Сочетает в себе элементы классицизма и новых для начала XIX века романтизма и реализма.", 3.5,R.drawable.monk2));
    }
}
