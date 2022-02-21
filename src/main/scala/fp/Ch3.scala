package fp

import scala.annotation.tailrec


sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]
object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x,xs) => x + sum(xs)
  }
  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x,xs) => x * product(xs)
  }
  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  
  def tail[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(head, tail) => tail
  }
  
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(head, tail) => if (f(head)) dropWhile(tail, f) else Cons(head, dropWhile(tail, f))
  }
  
  def init[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(_, Nil) => Nil
    case Cons(head, tail) =>Cons(head, init(tail))
  }
  
  
  def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = as match {
    case Nil => z
    case Cons(head, tail) => f(head, foldRight(tail, z)(f))
  }
  
  def sum2(ns: List[Int]): Int = foldRight(ns, 0)((x, y) => x+y)
  
  def product2(ns: List[Double]): Double = foldRight(ns, 1.0)(_ * _)
  
  def length[A](as: List[A]): Int = foldRight(as, 0)((_, x) => x+1)
  
  @tailrec
  def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B = as match {
    case Nil => z
    case Cons(head, tail) => foldLeft(tail, f(z, head))(f)
  }
  
  def sum3(ns: List[Int]): Int = foldLeft(ns, 0)((x, y) => x+y)
  
  def product3(ns: List[Double]): Double = foldLeft(ns, 1.0)(_ * _)
  
  def length2[A](as: List[A]): Int = foldLeft(as, 0)((x, _) => x+1)
  
  def reverse[A](as: List[A]): List[A] = foldLeft(as, List[A]())((a, b) => Cons(b, a))
  
  def foldLeft2[A,B](as: List[A], z: B)(f: (B, A) => B): B = foldRight(reverse(as), z)((b, a) => f(a, b))
  
  def foldRight2[A,B](as: List[A], z: B)(f: (A, B) => B): B = foldLeft(reverse(as), z)((b, a) => f(a, b))
  
  def append[A](a1: List[A], a2: List[A]): List[A] = foldLeft(a2, a1)((l , a) => Cons(a, l))
  
  def concat[A](l: List[List[A]]): List[A] = foldRight(l, Nil: List[A])((l, a) => append(a, l))
  
  def inc (ns: List[Int]): List[Int] = foldRight(ns, Nil: List[Int])((a, b) => Cons(a+1, b) )
  
  def doubleToString (ns: List[Double]): List[String] = foldRight(ns, Nil: List[String])((a, b) => Cons(a.toString, b) )
  
  def map[A,B](as: List[A])(f: A => B): List[B] = foldRight(as, Nil: List[B])((a, b) => Cons(f(a), b))
  
  def filter[A](as: List[A])(f: A => Boolean): List[A] = foldRight(as, Nil: List[A])((a, b) => if(f(a))Cons(a, b) else b)
  
  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = concat(map(as)(f))
  
  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = (sup, sub) match {
    case (_, Nil) => true
      case(Nil, _) => false
    case(Cons(h1,t1), Cons(h2, t2)) => if(h2 == h1) hasSubsequence(t1, t2) else hasSubsequence(t1, Cons(h2, t2))
  }
}

object Ch3 extends App{
  val l = List(1,2,3,4,5)
  println(List.filter(l)(_%2 == 0))
}
