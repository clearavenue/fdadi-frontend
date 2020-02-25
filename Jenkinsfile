@Library('clearavenue/clearavenue-jenkins-sharedlib')_

devPipeline {
  app_name = 'fdadi'
  docker_user = 'clearavenuedocker'
  service_name = 'fdadi-frontend'
  service_port = 8081
  liveness_url = '/actuator/health'
  readiness_url = '/actuator/health'
}
