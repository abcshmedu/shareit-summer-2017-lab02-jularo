package edu.hm.shareit.businessLayer;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.persistence.IMediaDAO;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public class MediaService implements IMediaService {

    private final IMediaDAO mediaDAO;

    @Inject
    public MediaService(IMediaDAO mediaDAO) {
        this.mediaDAO = mediaDAO;
    }


    private String formatISBN(String isbn) {
        return isbn.trim().replace("-", "");
    }


    @Override
    public MediaServiceResult addBook(Book book) {

        if (getBook(book.getIsbn()) != null) {
            return MediaServiceResult.DUPLICATE_ISBN;
        }

        if (!isbnIsValid(book.getIsbn())) {
            return MediaServiceResult.INVALID_ISBN;
        }

        if (book.getAuthor().isEmpty() || book.getTitle().isEmpty()) {
            return MediaServiceResult.INCOMPLETE_ARGUMENTS;
        }

        mediaDAO.addBook(book);

        return MediaServiceResult.OK;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {

        if (getDisc(disc.getBarcode()) != null) {
            return MediaServiceResult.DUPLICATE_BARCODE;
        }

        if (!barcodeIsValid(disc.getBarcode())) {
            return MediaServiceResult.INVALID_BARCODE;
        }

        if (disc.getDirector().isEmpty() || disc.getTitle().isEmpty() || disc.getFsk() == -1) {
            return MediaServiceResult.INCOMPLETE_ARGUMENTS;
        }

        mediaDAO.addDisc(disc);


        return MediaServiceResult.OK;
    }

    @Override
    public List<Book> getBooks() {
        return mediaDAO.getBooks();
    }

    @Override
    public List<Disc> getDiscs() {
        return mediaDAO.getDiscs();
    }

    @Override
    public Book getBook(String isbn) {
        return mediaDAO.getBook(formatISBN(isbn));
    }

    @Override
    public Disc getDisc(String barcode) {
        return mediaDAO.getDisc(barcode);
    }

    @Override
    public MediaServiceResult updateBook(Book book, String isbn) {
        if (!book.getIsbn().isEmpty() && !book.getIsbn().equals(formatISBN(isbn))) {
            return MediaServiceResult.MODIFYING_ISBN_NOT_ALLOWED;
        }

        if (getBook(book.getIsbn()) == null) {
            return MediaServiceResult.ISBN_NOT_FOUND;
        }


        Book oldBook = (Book) getBook(formatISBN(isbn));

        String newTitle = book.getTitle() == null || book.getTitle().isEmpty() ? oldBook.getTitle() : book.getTitle();
        String newAuthor = book.getAuthor() == null || book.getAuthor().isEmpty() ? oldBook.getAuthor() : book.getAuthor();

        Book newBook = new Book(newTitle, newAuthor, oldBook.getIsbn());

        mediaDAO.updateBook(newBook);


        return MediaServiceResult.OK;
    }

    @Override
    public MediaServiceResult updateDisc(Disc disc, String barcode) {
        if (!disc.getBarcode().isEmpty() && !disc.getBarcode().equals(barcode)) {
            return MediaServiceResult.MODIFYING_BARCODE_NOT_ALLOWED;
        }

        if (getDisc(barcode) == null) {
            return MediaServiceResult.BARCODE_NOT_FOUND;
        }

        Disc oldDisc = (Disc) getDisc(barcode);

        String newTitle = disc.getTitle() == null || disc.getTitle().isEmpty() ? oldDisc.getTitle() : disc.getTitle();
        String newDirector = disc.getDirector() == null || disc.getDirector().isEmpty() ? oldDisc.getDirector() : disc.getDirector();
        int newfsk = disc.getFsk() == -1 ? oldDisc.getFsk() : disc.getFsk();

        Disc newDisc = new Disc(newTitle, oldDisc.getBarcode(), newDirector, newfsk);

        mediaDAO.updateDisc(newDisc);


        return MediaServiceResult.OK;
    }


    private boolean isbnIsValid(String isbnCode) {
        isbnCode = formatISBN(isbnCode);

        final int[] isbn = isbnCode.chars()
                .map(Character::getNumericValue)
                .toArray();

        int sum = 0;
        if (isbn.length == 10) {
            for (int i = 0; i < 10; i++) {
                sum += i * isbn[i]; //asuming this is 0..9, not '0'..'9'
            }

            if (isbn[9] == sum % 11) return true;
        } else if (isbn.length == 13) {

            for (int i = 0; i < 12; i++) {
                if (i % 2 == 0) {
                    sum += isbn[i]; //asuming this is 0..9, not '0'..'9'
                } else {
                    sum += isbn[i] * 3;
                }
            }

            if (isbn[12] == 10 - (sum % 10)) return true;
        }
        return false;
    }

    private boolean barcodeIsValid(String barcode) {
        if (barcode.length() == 13) {
            final int[] ean13 = barcode.chars()
                    .map(Character::getNumericValue)
                    .limit(12)
                    .toArray();

            return calculateCheckDigit(ean13) == Character.getNumericValue(barcode.charAt(12));
        }
        return false;
    }

    private int calculateCheckDigit(int[] digits) {
        int sum = 0;
        int multiplier = 3;
        for (int i = digits.length - 1; i >= 0; i--) {
            sum += digits[i] * multiplier;
            multiplier = (multiplier == 3) ? 1 : 3;
        }
        int sumPlus9 = sum + 9;
        int nextMultipleOfTen = sumPlus9 - (sumPlus9 % 10); // nextMultipleOfTen ist jetzt das nächste Vielfache von zehn
        return nextMultipleOfTen - sum;
    }


//    /**
//     * resetting the Maps for testing.
//     */
//    @Override
//    public void clearMap() {
//        books.clear();
//        discs.clear();
//    }
}
