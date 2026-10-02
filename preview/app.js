const canvas = document.getElementById('glyphCanvas');
const context = canvas.getContext('2d');
const frameLabel = document.getElementById('frameLabel');
const durationLabel = document.getElementById('durationLabel');

const GRID_SIZE = 13;
const CELL_SIZE = 40;
const PAD = 30;
const GRID_COLOR = 'rgba(255,255,255,0.12)';

let frames = [];
let frameIndex = 0;
let nextFrameAt = 0;

function toMatrix(payload) {
  const matrix = [];
  for (let row = 0; row < GRID_SIZE; row += 1) {
    matrix.push(payload.slice(row * GRID_SIZE, (row + 1) * GRID_SIZE));
  }
  return matrix;
}

function drawFrame(frame) {
  context.clearRect(0, 0, canvas.width, canvas.height);

  context.fillStyle = '#0d1014';
  context.fillRect(0, 0, canvas.width, canvas.height);

  const matrix = toMatrix(frame.p);

  for (let row = 0; row < GRID_SIZE; row += 1) {
    for (let col = 0; col < GRID_SIZE; col += 1) {
      const value = matrix[row][col] || 0;
      const x = PAD + col * CELL_SIZE;
      const y = PAD + row * CELL_SIZE;

      context.fillStyle = colorFor(value);
      context.fillRect(x, y, CELL_SIZE - 2, CELL_SIZE - 2);
    }
  }

  context.strokeStyle = GRID_COLOR;
  context.lineWidth = 1;
  for (let i = 0; i <= GRID_SIZE; i += 1) {
    const offset = PAD + i * CELL_SIZE - 1;
    context.beginPath();
    context.moveTo(PAD, offset);
    context.lineTo(PAD + GRID_SIZE * CELL_SIZE, offset);
    context.stroke();

    context.beginPath();
    context.moveTo(offset, PAD);
    context.lineTo(offset, PAD + GRID_SIZE * CELL_SIZE);
    context.stroke();
  }

  frameLabel.textContent = `${frameIndex + 1} / ${frames.length}`;
  durationLabel.textContent = `${frame.d} ms`;
}

function colorFor(value) {
  if (value <= 0) {
    return 'rgba(255,255,255,0.04)';
  }

  if (value < 70) {
    return '#210407';
  }

  if (value < 120) {
    return '#5f0913';
  }

  if (value < 180) {
    return '#b41625';
  }

  if (value < 240) {
    return '#ff5661';
  }

  return '#f5f7fb';
}

async function loadAnimation() {
  const response = await fetch('../app/src/main/assets/spider_mask.json');
  const data = await response.json();
  frames = data.frames;
  drawFrame(frames[0]);
  frameIndex = 0;
  nextFrameAt = performance.now() + frames[0].d;
  requestAnimationFrame(tick);
}

function tick(now) {
  if (!frames.length) {
    return;
  }

  if (now >= nextFrameAt) {
    frameIndex = (frameIndex + 1) % frames.length;
    drawFrame(frames[frameIndex]);
    nextFrameAt = now + frames[frameIndex].d;
  }

  requestAnimationFrame(tick);
}

loadAnimation().catch((error) => {
  context.clearRect(0, 0, canvas.width, canvas.height);
  context.fillStyle = '#1c1011';
  context.fillRect(0, 0, canvas.width, canvas.height);
  context.fillStyle = '#ff7d86';
  context.font = '20px Segoe UI, sans-serif';
  context.fillText('Preview failed to load animation', 48, 256);
  console.error(error);
});
