package br.inatel.frota.dao;

import java.util.List;

public interface IDAO<T> {
    void inserir(T obj);
    void atualizar(T obj);
    void deletar(int id);
    List<T> listar();
}
