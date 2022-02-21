package fp

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(get) =>Some(f(get))
  }
  
  
  def flatMap[B](f: A => Option[B]): Option[B] = map(f) getOrElse None
  
  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(get) =>get
  }
  def orElse[B >: A](ob: => Option[B]): Option[B] = this map (Some(_)) getOrElse ob
  def filter(f: A => Boolean): Option[A] = flatMap(a => if (f(a)) Some(a) else None)
  
  def lift[C,B](f: C => B): Option[C] => Option[B] = (x => x.map(f))
}

case object None extends Option[Nothing]

case class Some[A](get: A) extends Option[A]

object Ch4Option extends App{
  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  
  
}
