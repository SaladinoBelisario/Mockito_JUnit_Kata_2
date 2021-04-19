package controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hu.springbootrestfuljpa.books.controller.BookController;
import hu.springbootrestfuljpa.books.model.Book;
import hu.springbootrestfuljpa.books.model.Review;
import hu.springbootrestfuljpa.books.repository.BookRepository;

public class BookControllerTest {
	
	private static int ID = 2;
	private static int RELEASE = 22;
	private static String AUTHOR = "Homero";
	private static String TITLE = "Odisea";
	
	private static List<Review> REVIEW_LIST = new ArrayList<>();
	
	private static final Book BOOK  = new Book();	
	private static final Optional<Book> OPTIONAL_BOOK =  Optional.of(BOOK);
	private static final Optional<Book> OPTIONAL_BOOK_EMPTY =  Optional.empty();
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	BookController bookController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		BOOK.setAuthor(AUTHOR);
		BOOK.setId(ID);
		BOOK.setRelease(RELEASE);
		BOOK.setReviews(REVIEW_LIST);
		BOOK.setTitle(TITLE);
		
	}
	
	@Test
	public void retrieveAllBooksTest() {
		final Book book = new Book();
		Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
		
		final List<Book> response = bookController.retrieveAllBooks();
		assertNotNull(response);
		assertFalse(response.isEmpty());
		assertEquals(response.size(), 1);
		
	}
	
	@Test
	public void retrieveBookTest() {
		Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK);
		ResponseEntity<Book> response = bookController.retrieveBook(ID);
		assertEquals(response.getBody().getAuthor(), AUTHOR);
		assertEquals(response.getBody().getTitle(), TITLE);
	
	}
	
	@Test
	public void retrieveBookNotFountTest() {
		Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK_EMPTY);
		ResponseEntity<Book> httpResponse = bookController.retrieveBook(ID);
		assertEquals(httpResponse.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void createBookTest() {
		Mockito.when(bookRepository.existsById(BOOK.getId())).thenReturn(false);
		
		ResponseEntity<Object> httpResponse = bookController.createBook(BOOK);
		assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void createBookExistsByIdTest() {
		Mockito.when(bookRepository.existsById(BOOK.getId())).thenReturn(true);
		
		ResponseEntity<Object> httpResponse = bookController.createBook(BOOK);
		assertEquals(httpResponse.getStatusCode(), HttpStatus.CONFLICT);
	}
	
	@Test
	public void deleteBookTest() {
		Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK);
		bookController.deleteBook(ID);
	}
	
	@Test
	public void deleteBookNotFountTest() {
		Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK_EMPTY);
		bookController.deleteBook(ID);
		ResponseEntity<Object> httpResponse = bookController.deleteBook(ID);
		assertEquals(httpResponse.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}
