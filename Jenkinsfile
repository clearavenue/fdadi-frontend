@Library('clearavenue/clearavenue-jenkins-sharedlib@externalinternal')_

mavenDevsecopsPipeline {
  app_name = 'fdadi'
  docker_user = 'clearavenuedocker'
  service_name = 'fdadi-frontend'
  service_port = 8081
  liveness_url = '/actuator/health'
  readiness_url = '/actuator/health'
  deploymentFile = 'external-deployment.yaml'
}
