#!/usr/bin/env bash
set -euo pipefail

KTLINT_VERSION="1.8.0"

docker build -f ktlint.dockerfile -t ktlint:${KTLINT_VERSION} .

echo "🔧 Running ktlint formatter (v${KTLINT_VERSION})..."
docker run --rm \
  -v "$(pwd):/work" \
  -w /work \
  "ktlint:${KTLINT_VERSION}" \
  --format "src/**/*.kt"

echo "✅ Done! All Kotlin files formatted."
