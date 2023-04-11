package br.com.ada.figurinhas.service;

public interface Redis {

  void save(String key, String value);
  String get(String key);
  
}
