package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.model.Book;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    public BookDTO findById(Long id) {
        return bookMapper.map(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Book with id %s not found",id))));
    }
    public BookDTO create(BookCreateDTO bookCreateDTO) {
        Book book = bookMapper.map(bookCreateDTO);
        return  bookMapper.map(bookRepository.save(book));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDTO update(BookUpdateDTO bookUpdateDTO, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Author with id %s not found",id)));
        bookMapper.update(bookUpdateDTO,book);
        return  bookMapper.map(bookRepository.save(book));
    }

}
