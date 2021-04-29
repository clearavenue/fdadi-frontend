@Library('clearavenue/clearavenue-jenkins-sharedlib@dependencyCheck')_

mavenDevsecopsPipeline {
  app_name = 'fdadi'
  docker_user = 'clearavenuedocker'
  service_name = 'fdadi-frontend'
  service_port = 8081
  liveness_url = 'actuator/health'
  readiness_url = 'actuator/health'
  host_name = 'devsecops.clearavenue.com'
  deploymentFile = 'external-deployment.yaml'
}
