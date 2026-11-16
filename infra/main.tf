terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {
  host = "unix:///Users/suciistachotiljannah/.docker/run/docker.sock"
}

variable "node_count" {
  default = 3
}

resource "docker_network" "grid" {
  name = "tf-grid"
}

resource "docker_image" "hub" {
  name = "selenium/hub:4.27.0"
}

resource "docker_image" "chromium" {
  name = "selenium/node-chromium:4.27.0"
}

resource "docker_container" "hub" {
  name  = "tf-selenium-hub"
  image = docker_image.hub.image_id
  networks_advanced { name = docker_network.grid.name }
  ports {
    internal = 4444
    external = 4444
  }
}

resource "docker_container" "node" {
  count    = var.node_count
  name     = "tf-chrome-${count.index + 1}"
  image    = docker_image.chromium.image_id
  shm_size = 2048
  networks_advanced { name = docker_network.grid.name }
  env = [
    "SE_EVENT_BUS_HOST=${docker_container.hub.name}",
    "SE_EVENT_BUS_PUBLISH_PORT=4442",
    "SE_EVENT_BUS_SUBSCRIBE_PORT=4443",
  ]
}

output "grid_url" {
  value = "http://localhost:4444"
}