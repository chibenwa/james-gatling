package org.apache.james.gatling.simulation.smtp

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import org.apache.james.gatling.simulation.{Configuration, HttpSettings, SimulationOnMailCorpus, UsersTotal}
import org.apache.james.gatling.smtp.scenari.SmtpNoAuthenticationNoEncryptionBigBodyScenario

class SmtpNoAuthenticationNoEncryptionBigBodySimulation extends Simulation with SimulationOnMailCorpus {
  setUp(
    injectUsers(new SmtpNoAuthenticationNoEncryptionBigBodyScenario()
      .generate(Configuration.ScenarioDuration, feeder)))

  def injectUsers(scenario: ScenarioBuilder) = {
    scenario
      .inject(UsersTotal(Configuration.UserCount).injectDuring(Configuration.InjectionDuration))
      .protocols(HttpSettings.httpProtocol)
  }
}
