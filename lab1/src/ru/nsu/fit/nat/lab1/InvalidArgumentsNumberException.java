package ru.nsu.fit.nat.lab1;

public class InvalidArgumentsNumberException extends Exception{

    public InvalidArgumentsNumberException(int expectedNumber, int realNumber) {
        super(String.format("Wrong number of arguments, %d expected, but %d was received!",expectedNumber,realNumber));
    }
}
