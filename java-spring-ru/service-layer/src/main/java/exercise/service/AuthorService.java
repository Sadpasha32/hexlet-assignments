package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
    public AuthorDTO findById(Long id) {
        return authorMapper.map(authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Author with id %s not found",id))));
    }
    public AuthorDTO create(AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.map(authorCreateDTO);
        return  authorMapper.map(authorRepository.save(author));
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDTO update(AuthorUpdateDTO authorUpdateDTO, Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Author with id %s not found",id)));
        authorMapper.update(authorUpdateDTO,author);
        return  authorMapper.map(authorRepository.save(author));
    }

}
