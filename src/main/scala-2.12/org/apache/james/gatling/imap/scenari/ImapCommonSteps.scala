package org.apache.james.gatling.imap.scenari

import java.util.Calendar

import com.linagora.gatling.imap.PreDef._
import com.linagora.gatling.imap.protocol.command.FetchAttributes.AttributeList
import com.linagora.gatling.imap.protocol.command.MessageRange.Last
import com.linagora.gatling.imap.protocol.command.MessageRanges
import io.gatling.core.Predef._

import scala.util.Random

object ImapCommonSteps {
  private val myRandom = Random.alphanumeric
  private def generateMessage() : String =
    myRandom.grouped(200).flatMap(_.append(Stream('\r', '\n'))).take(1024 * 1024).mkString

  val receiveEmail = exec(imap("append").append("INBOX", Some(scala.collection.immutable.Seq("\\Flagged")), Option.empty[Calendar],
    s"""From: expeditor@example.com
      |To: recipient@example.com
      |Subject: test subject
      |
      |Test content
      |${generateMessage()}
      |0123456789""".stripMargin).check(ok))

  val readLastEmail = exec(imap("list").list("", "*").check(ok, hasFolder("INBOX")))
    .exec(imap("select").select("INBOX").check(ok))
    .exec(imap("fetch").fetch(MessageRanges(Last()), AttributeList("UID", "BODY[HEADER]", "BODY[TEXT]")).check(ok))
}
