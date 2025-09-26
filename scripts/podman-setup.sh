#!/bin/bash

echo "🚀 Setting up Podman for ecommerce API project..."

# Check if Podman is installed
if ! command -v podman &> /dev/null; then
    echo "❌ Podman not found. Installing via Homebrew..."
    brew install podman
fi

# Check if podman-compose is installed
if ! command -v podman-compose &> /dev/null; then
    echo "❌ podman-compose not found. Installing..."
    brew install podman-compose
fi

# Initialize Podman machine if not exists
if ! podman machine list | grep -q "podman-machine-default"; then
    echo "🔧 Initializing Podman machine..."
    podman machine init --cpus 4 --memory 6144 --disk-size 20
fi

# Start Podman machine
echo "🔄 Starting Podman machine..."
podman machine start

# Install podman-mac-helper
echo "🔧 Installing podman-mac-helper for Docker compatibility..."
sudo /opt/homebrew/bin/podman-mac-helper install 2>/dev/null || \
sudo /usr/local/bin/podman-mac-helper install 2>/dev/null || \
echo "⚠️  podman-mac-helper installation may have failed. Try manual installation."

# Set environment variables
export DOCKER_HOST="unix://$(podman machine inspect --format '{{.ConnectionInfo.PodmanSocket.Path}}')"
export COMPOSE_DOCKER_CLI_BUILD=0

echo "✅ Podman setup complete!"
echo "🔗 DOCKER_HOST set to: $DOCKER_HOST"
echo ""
echo "Add the following to your ~/.zshrc or ~/.bash_profile:"
echo "export DOCKER_HOST=\"unix://\$(podman machine inspect --format '{{.ConnectionInfo.PodmanSocket.Path}}')\""
echo "export COMPOSE_DOCKER_CLI_BUILD=0"
echo "alias docker=podman"
