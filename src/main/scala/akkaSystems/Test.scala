package akkaSystems

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.annotation.tailrec

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]
object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }
  
  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }
  
  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  
  def fill[A](n: Int, a: A): List[A] = {
    @tailrec
    def go(n: Int, a: A, list: List[A]): List[A] = {
      n match {
        case 0 => list
        case m => go(n - 1, a, Cons(a, list))
      }
    }
  
    go(n, a, Nil)
  }
  
  def removeFirst[A](list: List[A]): List[A] = list match {
    case Nil => Nil
    case Cons(_, tail) => tail
  }
  
  def setHead[A](a: A, list: List[A]): List[A] = list match {
    case Nil => Nil
    case Cons(_, tail) => Cons(a, tail)
  }
  
  @tailrec
  def dropN[A](n: Int, list: List[A]): List[A] = list match {
    case Nil => list
    case Cons(_, tail) if n > 0 => dropN(n - 1, tail)
    case l if n == 0 => l
  }
  
  def init[A](list: List[A]): List[A] = {
    def go(list: List[A]): List[A] = list match {
      case Nil => Nil
      case Cons(_, Nil) => Nil
      case Cons(head, tail) => Cons(head, go(tail))
    }
    go(list)
  }
  
  def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = as match {
    case Nil => z
    case Cons(head, tail) => f(head, foldRight(tail, z)(f))
  }
  
  def length[A](as: List[A]): Int = foldRight(as, 0)((_,b) => 1+b)
  
  @tailrec
  def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B = as match {
    case Nil => z
    case Cons(head, tail) => foldLeft(tail, f(z, head))(f)
  }
  
  def sumL(ints: List[Int]): Int = foldLeft(ints, 0)(_ + _)
  
  def productL(ds: List[Double]): Double = foldLeft(ds, 1.0)(_ * _)
  
  def reverse[A](l: List[A]): List[A] = foldLeft(l, List[A]())((acc,h) => Cons(h,acc))
}


object Test extends App{
  def fib(n: Int): Int = {
    @tailrec
    def go(n: Int, first: Int, second: Int): Int = {
      if (n == 0)
        first
      else go(n-1, second, second + first)
    }
    if (n == 0) 0
    else if (n == 1) 1
    else go(n, 0 , 1)
  }
  
  
  def isSorted[A](as: Array[A], ordered: (A,A) => Boolean): Boolean = {
    @tailrec
    def loop(n:Int): Boolean = {
      if (n+1 >= as.size) true
      else if (ordered(as(n), as(n+1))) loop(n+1)
      else false
    }
    loop(0)
  }
  
  
  def curry[A,B,C] (f: (A, B) => C) : A=>B=>C= {
    a => b => f(a, b)
  }
  
  def uncurry[A,B,C](f: A => B => C): (A, B) => C = {
    (a, b) => f(a)(b)
  }
  
  def compose[A,B,C](f: B => C, g: A => B): A => C = {
    a => f(g(a))
  }
  
  val v = List.length(List(1,2,3))
  println(v)
}

