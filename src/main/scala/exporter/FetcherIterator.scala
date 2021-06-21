package exporter

class FetcherIterator[A, B, O](
    fetch: (O) => Option[A],
    nextPred: (A) => Boolean,
    joiner: (Option[A]) => B,
    initialOffset: O,
    offsetExtractor: (A) => O
) extends Iterator[B] {
  var started: Boolean = false
  var current: Option[A] = None

  def hasNext: Boolean = !started || current.isDefined && nextPred(current.get)

  def next(): B = {
      current = fetch(started match {
          case true => offsetExtractor(current.get)
          case false => {
              started = true
              initialOffset
          }
      })
      joiner(current)
  }
}
