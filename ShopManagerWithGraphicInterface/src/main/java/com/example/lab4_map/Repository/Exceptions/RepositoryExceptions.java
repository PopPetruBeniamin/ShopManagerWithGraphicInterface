package com.example.lab4_map.Repository.Exceptions;

public class RepositoryExceptions {
    public static class EntityNotFoundException extends RuntimeException{
        public EntityNotFoundException(String message){
            super(message);
        }
    }
    public static class EntityNotAddedException extends RuntimeException{
        public EntityNotAddedException(String message){
            super(message);
        }
    }
    public static class RepositoryException extends RuntimeException{
        public RepositoryException(String message){
            super(message);
        }
    }
}
