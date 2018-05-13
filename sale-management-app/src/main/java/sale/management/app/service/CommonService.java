package sale.management.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sale.management.app.repository.CommonRepository;

import java.util.Optional;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

public abstract class CommonService<T>
{
    @Autowired
    private CommonRepository<T> repository;

    public T save(T t)
    {
        return repository.save(t);
    }

    public void delete(String id)
    {
        repository.findById(id).ifPresent(t -> repository.delete(t));
    }

    public Optional<T> getById(String id)
    {
        return repository.findById(id);
    }

    public Page<T> getAll(Pageable pageable)
    {
        return repository.findAll(pageable);
    }

    public abstract Page<T> search(T t, Pageable pageable);
}
