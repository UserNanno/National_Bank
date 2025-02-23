package com.nationalbank.nationalbankperu.service;

import com.nationalbank.nationalbankperu.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return una lista de usuarios.
     */
    List<User> findAll();

    /**
     * Busca un usuario por su ID.
     *
     * @param id el identificador del usuario.
     * @return un `Optional<User>` que puede contener el usuario si existe.
     */
    Optional<User> findById(Long id);

    /**
     * Guarda o actualiza un usuario en la base de datos.
     *
     * @param user el usuario a guardar o actualizar.
     * @return el usuario guardado.
     */
    User save(User user);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id el identificador del usuario a eliminar.
     */
    void deleteById(Long id);

    /**
     * Verifica si un usuario existe por su número de identificación.
     *
     * @param numIdentification número de identificación del usuario.
     * @return `true` si existe, `false` en caso contrario.
     */
    boolean existsByNumIdentification(String numIdentification);

    /**
     * Busca un usuario por su número de identificación.
     *
     * @param numIdentification número de identificación del usuario.
     * @return un `Optional<User>` con el usuario si existe.
     */
    Optional<User> findByNumIdentification(String numIdentification);
}
